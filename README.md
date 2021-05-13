# EKBCityTreeMap

## Запуск локально
### Используя docker-compose

[Установка docker-compose](https://docs.docker.com/compose/install/)

Находясь в корне проекта можно использовать следующие команды:

`make start` - запуск приложения

`make clear` - остановка приложения

`make full-clear`  - остановка приложения и полное удаление его данных

### Ручной запуск
Для работоспособности приложения ему необходимы: 
СУБД Postgres 12+, PostGis 3+ и любой S3-Like сервис

Необходимо поднять и настроить эти приложения, а затем указать соответствующие настройки в переменных окружения:
* `DATASOURCE_URL` - адрес базы данны 
* `DATASOURCE_LOGIN` - пользователь базы данных
* `DATASOURCE_PASSWORD` - пароль базы данных
* `S3_HOST` - адрес сервиса S3
* `S3_REGION` - регион сервиса S3
* `S3_ACCESS_KEY_ID` - идентификатор в сервисе S3
* `S3_SECRET_ACCESS_KEY` - секретный ключ в сервисе S3