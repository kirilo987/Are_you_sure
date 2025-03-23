package org.kxysl1k.are_you_sure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Are_you_sure extends JavaPlugin implements Listener {

    private Set<String> restrictedCommands = new HashSet<>();
    private boolean pluginEnabled = true;
    private Map<Player, String> pendingCommands = new HashMap<>(); // Сохраняет команды, которые ожидают подтверждения

    @Override
    public void onEnable() {
        // Загружаем конфигурацию
        saveDefaultConfig();
        loadRestrictedCommands();

        // Регистрируем события
        getServer().getPluginManager().registerEvents(this, this);

        // Регистрируем команды
        getCommand("areyousure").setExecutor(this);
        getCommand("sure_yes").setExecutor(new SureYesCommand());
        getCommand("sure_no").setExecutor(new SureNoCommand());

        getLogger().info("Плагин AreYouSure включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагин AreYouSure выключен!");
    }

    // Загрузка команд из конфигурации
    private void loadRestrictedCommands() {
        restrictedCommands.clear();
        restrictedCommands.addAll(getConfig().getStringList("restricted-commands"));
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!pluginEnabled) return; // Если плагин выключен, игнорируем событие

        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1); // Удаляем "/"

        // Проверяем, требует ли команда подтверждения
        if (restrictedCommands.contains(command)) {
            event.setCancelled(true); // Отменяем команду

            // Сохраняем команду для подтверждения
            pendingCommands.put(player, event.getMessage());

            // Создаем сообщение с кнопками
            Component message = Component.text("Вы уверены, что хотите выполнить команду? ", NamedTextColor.YELLOW)
                    .append(Component.text("[Да]", NamedTextColor.GREEN)
                            .clickEvent(ClickEvent.runCommand("/sure_yes")))
                    .append(Component.text(" "))
                    .append(Component.text("[Нет]", NamedTextColor.RED)
                            .clickEvent(ClickEvent.runCommand("/sure_no")));

            player.sendMessage(message);

            // Логируем попытку выполнения команды
            logAction(player, event.getMessage(), false);
        }
    }

    // Команда для подтверждения ("Да")
    private class SureYesCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Проверяем, есть ли команда для подтверждения
                if (pendingCommands.containsKey(player)) {
                    String originalCommand = pendingCommands.get(player);
                    pendingCommands.remove(player);

                    // Выполняем оригинальную команду
                    getServer().dispatchCommand(player, originalCommand.substring(1));

                    // Логируем подтверждение
                    logAction(player, originalCommand, true);
                } else {
                    sender.sendMessage(Component.text("Нет команды для подтверждения.", NamedTextColor.RED));
                }
                return true;
            }
            return false;
        }
    }

    // Команда для отмены ("Нет")
    private class SureNoCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Проверяем, есть ли команда для отмены
                if (pendingCommands.containsKey(player)) {
                    String originalCommand = pendingCommands.get(player);
                    pendingCommands.remove(player);

                    // Логируем отмену
                    logAction(player, originalCommand, false);

                    sender.sendMessage(Component.text("Команда отменена.", NamedTextColor.RED));
                } else {
                    sender.sendMessage(Component.text("Нет команды для отмены.", NamedTextColor.RED));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("areyousure")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    pluginEnabled = true;
                    sender.sendMessage(Component.text("Плагин AreYouSure включен!", NamedTextColor.GREEN));
                    return true;
                } else if (args[0].equalsIgnoreCase("off")) {
                    pluginEnabled = false;
                    sender.sendMessage(Component.text("Плагин AreYouSure выключен!", NamedTextColor.RED));
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    loadRestrictedCommands();
                    sender.sendMessage(Component.text("Конфигурация плагина перезагружена!", NamedTextColor.GOLD));
                    return true;
                }
            }
            sender.sendMessage(Component.text("Использование: /areyousure <on|off|reload>", NamedTextColor.YELLOW));
            return true;
        }
        return false;
    }

    // Логирование действий
    private void logAction(Player player, String command, boolean confirmed) {
        String logEntry = String.format(
                "[%s] %s попытался выполнить команду: %s (Подтверждено: %s)",
                LocalDateTime.now(),
                player.getName(),
                command,
                confirmed ? "Да" : "Нет"
        );

        // Записываем в консоль
        getLogger().info(logEntry);

        // Записываем в файл log.yml
        try (FileWriter writer = new FileWriter(getDataFolder() + "/log.yml", true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            getLogger().warning("Не удалось записать лог: " + e.getMessage());
        }
    }
}