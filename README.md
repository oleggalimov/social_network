# Сборка и запуск приложения
## Старт приложения
В корне проекта выполняем последовательно:
```sh
mvn clean install
```

```sh
docker build -t social-network-dialogs:0.1.0 ./social-network-dialogs
```

```sh
docker build -t social-network-monolith:0.1.0 ./social-network-monolith
```

Остановка ранее запущенного compose
```shell
docker compose down
```

Запуск в режиме full (включая кеши, тарантул и т.д.)
```shell
docker compose --profile full up -d
```

Запуск в режиме standalone (Только база данных, все остальное не работает)
```shell
docker compose --profile standalone up -d
```


## Настройка web-socket через MQ

В контейнере Rabbit mq включить плагин для обработки STOMP и создать целевой exchange:
```shell
rabbitmq-plugins enable rabbitmq_stomp
```
```shell
rabbitmqadmin declare exchange name=post type=topic
```

## Настройка Tarantool
Скопируйте содежимое каталога `perfomance-test-tarantool` в каталог вольюма образа - `$PWD/volumes/tarantool/`

С помощью [Cartridge CLI](https://www.tarantool.io/ru/doc/latest/book/cartridge/cartridge_cli/installation/)
подключитесь к запущенному контейнеру и запустите скрипт инициализации пространства:

```shell
cartridge connect localhost:3301 -u test_user -p test_password
```

```shell
dofile('/opt/tarantool/users.lua');
```

## Примечание

В корне проекта лежит [коллекция для Postman](social-network-monolith/oleg_galimov_social_network.postman_collection.json),
ее можно искользовать для тестирования API.
Примерная последовательность шагов:
- создать пользователя;
- залогиниться под новым пользователем (используя новый user_id и пароль);
- воспользоваться поисковым апи, при этом подставляя Authorisation Bearer хедер (полученный при логине).