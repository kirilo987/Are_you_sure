### English Version

```markdown
# Are_you_sure Plugin

## Overview
The `Are_you_sure` plugin is designed to add an extra layer of confirmation for executing certain commands in Minecraft. This helps prevent accidental or unauthorized use of powerful commands.

## Features
- Restricts the execution of specified commands until confirmed by the player.
- Provides a simple interface for enabling, disabling, and reloading the plugin.
- Logs all attempts to execute restricted commands.

## Commands
- `/areyousure <on|off|reload>`: Enable, disable, or reload the plugin configuration.
- `/sure_yes`: Confirm the execution of a pending command.
- `/sure_no`: Cancel the execution of a pending command.

## Permissions
- `areyousure.admin`: Required to use the `/areyousure` command.

## Configuration
The plugin configuration is stored in `config.yml`. You can specify which commands require confirmation under the `restricted-commands` section.

```yaml
restricted-commands:
  - op
  - ban
  - pardon
  - gamemode
  - time
  - weather
  - difficulty
  - effect
  - enchant
  - give
  - clear
  - kill
  - tp
  - spawnpoint
  - setworldspawn
  - setblock
  - fill
```

## Installation
1. Download the plugin JAR file.
2. Place the JAR file in the `plugins` directory of your Minecraft server.
3. Start or restart your server to load the plugin.
4. Configure the plugin by editing the `config.yml` file in the `plugins/Are_you_sure` directory.
5. Use the `/areyousure reload` command to apply configuration changes.

## License
This project is licensed under the MIT License. See the `MIT License.txt` file for details.
```

### Russian Version

```markdown
# Плагин Are_you_sure

## Обзор
Плагин `Are_you_sure` предназначен для добавления дополнительного уровня подтверждения при выполнении определенных команд в Minecraft. Это помогает предотвратить случайное или несанкционированное использование мощных команд.

## Возможности
- Ограничивает выполнение указанных команд до тех пор, пока игрок не подтвердит их выполнение.
- Предоставляет простой интерфейс для включения, отключения и перезагрузки плагина.
- Логирует все попытки выполнения ограниченных команд.

## Команды
- `/areyousure <on|off|reload>`: Включить, выключить или перезагрузить конфигурацию плагина.
- `/sure_yes`: Подтвердить выполнение ожидающей команды.
- `/sure_no`: Отменить выполнение ожидающей команды.

## Права доступа
- `areyousure.admin`: Необходимо для использования команды `/areyousure`.

## Конфигурация
Конфигурация плагина хранится в файле `config.yml`. Вы можете указать, какие команды требуют подтверждения, в разделе `restricted-commands`.

```yaml
restricted-commands:
  - op
  - ban
  - pardon
  - gamemode
  - time
  - weather
  - difficulty
  - effect
  - enchant
  - give
  - clear
  - kill
  - tp
  - spawnpoint
  - setworldspawn
  - setblock
  - fill
```

## Установка
1. Скачайте JAR-файл плагина.
2. Поместите JAR-файл в директорию `plugins` вашего сервера Minecraft.
3. Запустите или перезапустите сервер, чтобы загрузить плагин.
4. Настройте плагин, отредактировав файл `config.yml` в директории `plugins/Are_you_sure`.
5. Используйте команду `/areyousure reload`, чтобы применить изменения конфигурации.

## Лицензия
Этот проект лицензирован по лицензии MIT. Подробности см. в файле `MIT License.txt`.
```
