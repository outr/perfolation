#!/usr/bin/env bash

sbt +macrosJS/publishLocal +macrosJVM/publishLocal +coreJS/publishLocal +coreJVM/publishLocal +unitJS/publishLocal +unitJVM/publishLocal ++2.11.12 macrosNative/publishLocal coreNative/publishLocal unitNative/publishLocal