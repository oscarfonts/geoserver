host="https://www.transifex.com"
project_slug="geoserver_test"

propfile_orig="GeoServerApplication.properties"
propfile_trans="GeoServerApplication_<lang>.properties"

tx init --host $host
echo "type = PROPERTIES" >> .tx/config

find . -name $propfile_orig | while read -r source_file; do
  if [[ $source_file =~ \./(maven/archetype/webPlugin/src/main/resources/)?((.*)/src/main/(resources|java))/$propfile_orig ]]; then
     resource_slug=`echo ${BASH_REMATCH[3]} | sed 's/\//_/g'`
     file_filter="${BASH_REMATCH[1]}${BASH_REMATCH[2]}/$propfile_trans"
     tx set --auto-local -r $project_slug.$resource_slug "$file_filter" --source-lang en --source-file $source_file --execute
  fi
done

echo "---"
echo "Transifex initialized for the repo: $host/projects/p/$project_slug/"
