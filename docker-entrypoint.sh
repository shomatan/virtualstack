#!/usr/bin/env sh
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

exec /app/bin/virtualstack "$@"