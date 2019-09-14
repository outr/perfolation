#!/usr/bin/env bash

sbt +clean +compile +macrosJS/publishSigned +macrosJVM/publishSigned +coreJS/publishSigned +coreJVM/publishSigned +unitJS/publishSigned +unitJVM/publishSigned ++2.11.12 macrosNative/publishSigned coreNative/publishSigned unitNative/publishSigned sonatypeRelease