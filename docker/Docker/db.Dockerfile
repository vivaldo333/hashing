FROM mysql:5.7
COPY setup.sql /docker-entrypoint-initdb.d/
#CMD mysql -u root -p root ksdb < setup.sql