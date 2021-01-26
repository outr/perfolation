#!/usr/bin/env bash

sbt +clean +compile +test +coreJS/publishSigned +coreJVM/publishSigned +coreNative/publishSigned +unitJS/publishSigned +unitJVM/publishSigned +unitNative/publishSigned sonatypeRelease