# DCNI_SUM_RABBITMQ
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Ejecutar docker compose por nombre
docker compose -f docker-compose-rabbitmq.yml up

# Docker hub repos

espanderlof/dcni_sum_rabbitmq_productor1
espanderlof/dcni_sum_rabbitmq_productor2
espanderlof/dcni_sum_rabbitmq_consumidor1
espanderlof/dcni_sum_rabbitmq_consumidor2

sudo docker compose -f docker-compose-hub-microservices.yml up


# volver a iniciar un contenedor ya creado en docker MV azure
1. traer todos los conenedores
    docker ps -a
1.1 en caso de actualizar el contenedor realizar un pull primero    
    docker pull espanderlof/alertas_medicas:latest
    docker pull espanderlof/dcni_sum_rabbitmq_productor1:latest
    docker pull espanderlof/dcni_sum_rabbitmq_productor2:latest
    docker pull espanderlof/dcni_sum_rabbitmq_consumidor1:latest
    docker pull espanderlof/dcni_sum_rabbitmq_consumidor2:latest
    
1.2 eliminar el contenedor actual
    docker rm alertas_medicas_backend
    docker rm amed_productor1
    docker rm amed_productor2
    docker rm amed_consumidor1
    docker rm amed_consumidor2

1.3 volver a levandar el contenedor
    sudo docker run -d -p 8081:8081 --name alertas_medicas_backend espanderlof/alertas_medicas:latest
    sudo docker run -d -p 8082:8082 --name amed_productor1 espanderlof/dcni_sum_rabbitmq_productor1:latest
    sudo docker run -d -p 8084:8084 --name amed_productor2 espanderlof/dcni_sum_rabbitmq_productor2:latest
    sudo docker run -d -p 8083:8083 --name amed_consumidor1 espanderlof/dcni_sum_rabbitmq_consumidor1:latest
    sudo docker run -d -p 8085:8085 --name amed_consumidor2 espanderlof/dcni_sum_rabbitmq_consumidor2:latest

2.  llamar al contenedor
    sudo docker start alertas_medicas_backend
3. revisar si se ejecuto el contenedor
    docker ps

url publica rabbitmq http://172.210.177.28:15672/

# levantar contenedores
sudo docker start alertas_medicas_backend
sudo docker start rabbitmq
sudo docker start amed_productor1
sudo docker start amed_productor2
sudo docker start amed_consumidor1
sudo docker start amed_consumidor2


# ver log tiempo real contenedor

docker logs -f amed_productor1
docker logs -f amed_productor2
docker logs -f amed_consumidor1
docker logs -f amed_consumidor2
