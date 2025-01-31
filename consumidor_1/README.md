# DCNI_SUM_RABBITMQ - CONSUMIDOR 1
DESARROLLO CLOUD NATIVE I - SUMATIVA

# Iniciar Springboot
mvn spring-boot:run

# Limpia y recompila el proyecto
mvn clean install

# Levantar contenedor Docker
docker build -t amed_consumidor1 .
docker run --name amed_consumidor1 -p 8083:8083 amed_consumidor1