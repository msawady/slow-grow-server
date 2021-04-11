#!/bin/sh
set -eu

DOCKER_POSTGRES_NAME=$(docker ps --format "table {{.Names}}\t{{.Ports}}" | /usr/bin/grep 5432 | awk '{print $1}')
NETWORK_ID=$(docker inspect "${DOCKER_POSTGRES_NAME}" | grep NetworkID | cut -d'"' -f4)
ALIAS_EXISTS=$(docker inspect "${DOCKER_POSTGRES_NAME}" | grep Aliases | grep -c "null") && echo "Aliases was not set." || echo "Aliases was set."

docker_flyway() {
  CONFIG_FILE=$1
  shift
  NW_OPTION=$(test "${ALIAS_EXISTS}" -eq 1 && echo "--link ${DOCKER_POSTGRES_NAME}" || echo "--net=${NETWORK_ID}")
  COMMAND=$*
  docker run ${NW_OPTION} --rm \
    -v "${PWD}"/database/:/flyway/database boxfuse/flyway:6.0.0-beta2 \
    -configFiles="${CONFIG_FILE}" \
    -url=$(grep url "${CONFIG_FILE}" | sed -E "s/(localhost|127.0.0.1)/${DOCKER_POSTGRES_NAME}/" | sed "s/flyway.url=//") \
    ${COMMAND}
}

# create db
docker login
docker exec "$DOCKER_POSTGRES_NAME" psql -U root -tc "SELECT 1 FROM pg_database WHERE datname = 'slow_grow'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE slow_grow"

# echo 127.0.0.1:5432:slow_grow:root:root > ~/.pgpass

# migrate
## clean
docker_flyway database/flyway/local.conf clean

## migrate
docker_flyway database/flyway/local.conf baseline migrate validate
