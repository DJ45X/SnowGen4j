#!/bin/bash

# Define source directory and main class
SRC_DIR="src/main/java"
MAIN_CLASS="com.snowflakecsvtool.CsvIdInjector"
CONFIG_CLASS="com.snowflakecsvtool.config.EurekaConfig"
UTIL_CLASS="com.snowflakecsvtool.util.SnowflakeIdGenerator"

# Output directory for class files (optional, classes can be created alongside sources)
# For simplicity, we'll compile directly and let java find them via classpath from src/main/java
# If we had an out dir:
# OUT_DIR="out/production/snowflakecsvtool"
# mkdir -p "$OUT_DIR"

echo "Compiling Java source files..."

# Compile the Java files
# Adjust classpath if using an output directory
javac \
  "$SRC_DIR/com/snowflakecsvtool/config/EurekaConfig.java" \
  "$SRC_DIR/com/snowflakecsvtool/util/SnowflakeIdGenerator.java" \
  "$SRC_DIR/com/snowflakecsvtool/CsvIdInjector.java"

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Compilation successful."

# Check if a CSV file argument is provided
if [ -z "$1" ]; then
    echo "Usage: ./run.sh <path_to_csv_file>"
    # Attempt to run with the default players_db_ready.csv if no arg is given and it exists
    if [ -f "players_db_ready.csv" ]; then
        echo "No CSV file provided, attempting to use default 'players_db_ready.csv'"
        INPUT_CSV="players_db_ready.csv"
    else
        echo "Error: No input CSV file specified and 'players_db_ready.csv' not found."
        exit 1
    fi
else
    INPUT_CSV="$1"
fi

echo "Running the application with input file: $INPUT_CSV"

# Run the Java application
# The classpath needs to point to the directory containing the top-level package 'com'
java -cp "$SRC_DIR" "$MAIN_CLASS" "$INPUT_CSV"

echo "Application finished."
