#!/bin/bash

for i in {1..10}
do
   curl ${1}:8080
   echo "\n"
done
