# Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved. This
# code is released under a tri EPL/GPL/LGPL license. You can use it,
# redistribute it and/or modify it under the terms of the:
#
# Eclipse Public License version 1.0
# GNU General Public License version 2
# GNU Lesser General Public License version 2.1

unless Truffle::Interop.mime_type_supported?('application/x-sl')
  puts 'SL doesn\'t appear to be available - skipping SL test'
  exit
end

Truffle::Interop.eval('application/x-sl', %{
function clamp(min, max, value) {
  if (value < min) {
    return min;
  }
  if (value > max) {
    return max;
  }
  return value;
}})

Truffle::Interop.import_method(:clamp)

if clamp(10, 90, 46) != 46
  abort 'result not as expected'
end
