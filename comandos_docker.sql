--Crear imagen a partir del Dokerfile
sudo docker build -t monitor-backend-apirest:v1 .

/*Para windows*/
docker build -t monitor-backend-apirest:v1 .
--##############################################
-- Ver los log de los container
sudo docker logs -f ID_container

/*Para windows*/
docker build -t monitor-backend-apirest:v1 .
--##############################################
--Ejecutar la imagen para crear el contenedor (el contenedor empieza a correr)
sudo docker run -p 8081:8081 --name container-app-monitor-epp --network springboot -d monitor-backend-apirest:v1


/*Para windows*/
docker run -p 8081:8081 --name container-app-monitor-epp -d monitor-backend-apirest:v1
--##############################################