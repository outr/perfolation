#!/usr/bin/env bash

sbt +clean +compile +test +coreJS/publishSigned +coreJVM/publishSigned +unitJS/publishSigned +unitJVM/publishSigned ++2.11.12 coreNative/publishSigned unitNative/publishSigned sonatypeRelease