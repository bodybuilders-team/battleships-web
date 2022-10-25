#!/bin/sh
# wait-for-postgres.sh host cmd

set -e

host="$1"
shift
# shellcheck disable=SC2124
cmd="$@"

echo >&2 "waiting for postgres on $host"
until pg_isready -h "$host"; do
  echo >&2 "Postgres is unavailable - sleeping"
  sleep 1
done

echo >&2 "Postgres is up - executing command '$cmd'"
exec "$cmd"
