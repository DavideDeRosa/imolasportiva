FROM postgres:16
ENV POSTGRES_PASSWORD=root
ENV POSTGRES_DB=imolasportiva
ENV PATH_VOLUME=/Users/davidederosa/Desktop/UNIBO/Tirocinio/volumes
VOLUME ${PATH_VOLUME} /var/lib/postgresql/data
COPY database/tables.sql /docker-entrypoint-initdb.d/tables.sql

#docker run --name db --network imolasportivanetwork -d postgres_custom:16
#docker start db