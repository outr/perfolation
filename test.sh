#!/usr/bin/env bash

set -e

sbt +clean +compile
sbt coreJVM/test coreJS/test