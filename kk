#!/bin/bash
#
# script to format input for RandMst
# 
# note that the user must compile with make before running this script

echo "Running KK..."
if [ $# -gt 1 ]; then
    echo "usage: numberpartition inputfile"
else
    java numberpartition $1
fi