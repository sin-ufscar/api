#!/usr/bin/env bash

docker run --name banco-postgres -e POSTGRES_DB=api -e POSTGRES_PASSWORD=api -p 7778:5432 -d postgres:9.5