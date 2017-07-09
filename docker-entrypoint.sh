#!/usr/bin/env bash
#
# -----------------------------------------------------------------------------

echo "** Preparing VirtualStack app"

echo "** Waiting for PostgreSQL"

export PGPASSWORD="${VIRTUALSTACK_DB_PASS}"

until psql -h "${VIRTUALSTACK_DB_HOST}" -U "${VIRTUALSTACK_DB_USER}" -c '\l'; do
  >&2 echo "**** PostgreSQL is unavailable - sleeping"
  sleep 1
done

echo "########################################################"

echo "** Executing VirtualStack app"

exec /app/bin/virtualstack \
        -Dslick.dbs.default.db.url="jdbc:postgresql://${VIRTUALSTACK_DB_HOST}/${VIRTUALSTACK_DB_NAME}" \
        -Dslick.dbs.default.db.user="${VIRTUALSTACK_DB_USER}" \
        -Dslick.dbs.default.db.password="${VIRTUALSTACK_DB_PASS}" \
        -Dplay.evolutions.db.default.autoApply=true \
        "$@"