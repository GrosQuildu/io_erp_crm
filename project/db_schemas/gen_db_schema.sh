#!/bin/bash

# postgres docker:
(sudo docker ps -a | grep scpostgres 1>/dev/null) || sudo docker run --name scpostgres \
-e POSTGRES_USER=schemacrawler \
-e POSTGRES_PASSWORD=schemacrawler \
-h postgres_host \
-d postgres

# create schemas, password is schemacrawler:
sudo docker run -it --rm -v $(pwd):/share  --link scpostgres:postgres postgres \
 psql -h postgres -U schemacrawler -f /share/init.sql

# schemaClawler docker:
sudo docker run \
--link scpostgres:postgres \
-v $(pwd):/share \
--rm -i -t \
--entrypoint=/bin/bash \
sualeh/schemacrawler

# generate (from inside docker):
./schemacrawler.sh \
-server=postgresql -host=postgres_host \
-user=schemacrawler -password=schemacrawler \
-database=schemacrawler \
-infolevel=standard -routines= -command=schema \
-outputformat=png  \
-o /share/schema_crm.png -schemas=.*crm.*
echo "CRM schema generated"

./schemacrawler.sh \
-server=postgresql -host=postgres_host \
-user=schemacrawler -password=schemacrawler \
-database=schemacrawler \
-infolevel=standard -routines= -command=schema \
-outputformat=png  \
-o /share/schema_erp.png -schemas=.*erp.*
echo "ERP schema generated"

./schemacrawler.sh \
-server=postgresql -host=postgres_host \
-user=schemacrawler -password=schemacrawler \
-database=schemacrawler \
-infolevel=standard -routines= -command=schema \
-outputformat=png  \
-o /share/schema.png
echo "All schemas generated"