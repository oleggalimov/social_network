# Сборка и запуск приложения
В корне проекта выполняем последовательно:
```sh
mvn clean install
```

```sh
docker build -t social-network:0.1.0 .
```
```shell
docker compose up -d
```

В контейнере Rabbit mq включить плагин для обработки STOMP и создать целевой exchange:
```shell
rabbitmq-plugins enable rabbitmq_stomp
```
```shell
rabbitmqadmin declare exchange name=post type=topic
```

В корне проекта лежит [коллекция для Postman](oleg_galimov_social_network.postman_collection.json),
ее можно искользовать для тестирования API.
Примерная последовательность шагов:
- создать пользователя;
- залогиниться под новым пользователем (используя новый user_id и пароль);
- воспользоваться поисковым апи, при этом подставляя Authorisation Bearer хедер (полученный при логине).