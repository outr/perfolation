#!/usr/bin/env bash

sbt +clean +publishSigned sonatypeRelease
