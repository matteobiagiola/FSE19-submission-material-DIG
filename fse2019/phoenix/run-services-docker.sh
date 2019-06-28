#!/bin/bash

# Start postgres
/etc/init.d/postgresql start

# Start elixir server
mix phoenix.server
