#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE quilltest;
    GRANT ALL PRIVILEGES ON DATABASE quilltest TO $POSTGRES_USER;
EOSQL