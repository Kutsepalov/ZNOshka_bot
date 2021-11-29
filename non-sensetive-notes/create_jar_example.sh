#!/bin/bash

export ENV_BOT_DATA="exaple1234567890"

mvn package
mvn install -D skipTests