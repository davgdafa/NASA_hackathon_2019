#!/usr/bin/env bash

# make executable: chmod u+x load.sh

read -p "Enter output directory: " dir
wget -e robots=off -m -np -R .html,.tmp -nH --cut-dirs=4 "https://nrt4.modaps.eosdis.nasa.gov/api/v2/content/archives/FIRMS/viirs/USA_contiguous_and_Hawaii" --header "Authorization: Bearer 7DE2F7A4-F2C5-11E9-A27D-72F3207B60E0" -P $dir
