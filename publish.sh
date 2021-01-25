#!/usr/bin/env bash

sbt +clean +compile +test +coreJS/publishSigned +coreJVM/publishSigned +unitJS/publishSigned +unitJVM/publishSigned +coreNative/publishSigned +unitNative/publishSigned sonatypeRelease