/*
 * Copyright (c) 2025 Brandon Mueller
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.snowflakecsvtool.config;

public class EurekaConfig {
    public long getCurrentDataCenterIn5Bit() {
        // 5 bits for data center ID (0 to 31
        return 0b11000L; // 24 represents the date of 02/16/2024 calculated to a single number to fit the constraint of 0 to 31 | formula: ((month × day) + (year % 100)) % 32
    }

    public long getCurrentInstanceIndex() {
        // Return machine ID (0 to 31)
        return 0b11110L; // 30 represents the date of 06/01/2024 calculated to a single number to fit the constraint of 0 to 31 | ((month × day) + (year % 100)) % 32
    }
}
