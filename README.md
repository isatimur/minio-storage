Сборка
------
Для сборки дистрибутива к корневом каталоге репа запускается команда:
```batch
sbt clean clean-files dist
```
После сборки в под-каталоге `\target\universal\` появится файл `minio-storage-1.0.zip` 

Запуск
------
Взять файл `minio-storage-1.0.zip`, сформированный на этапе сборки, раззиповать куда-либо и запустить приложение:
```batch
./minio-storage-1.0/bin/minio-storage
```

Тюнинг
------
По умолчанию приложение использует файл конфигурации `conf/application.conf`. В нём лежат все настройки программы, включая реквизиты доступа к базе данных.
Запустить приложение с использованием альтернативного конфигурационного (здесь -- `super.puper.conf`) файла можно так:
```batch
./minio-storage-1.0/bin/minio-storage -Dconfig.resource=super.puper.conf
```

Проверка
--------
При нормальном запуске приложение должно корректно реагировать на запрос GET /
т.е. оно должно выдать 200 OK и пустую страницу

Docker
--------
To make a `docker image` locally just run following command in **sbt tool**:
```
sbt docker:publishLocal
```
which will make your project-name:version image of docker

Docker-Compose
--------
To load all service on docker:
 - postgres
 - minio
 - minio-storage
 
you need to run following command:
```
docker-compose -f /docker/app.yml up -d
```