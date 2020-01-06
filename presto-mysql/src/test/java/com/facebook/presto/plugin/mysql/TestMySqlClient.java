/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.plugin.mysql;

import com.facebook.presto.plugin.jdbc.RangeInfo;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.facebook.presto.plugin.mysql.MySqlClient.convertPartitionDescriptionsToRangeInfos;
import static org.testng.Assert.assertEquals;

public class TestMySqlClient
{
    @Test
    public void testConvertPartitionDescriptionsToRangeInfos()
    {
        assertEquals(
                convertPartitionDescriptionsToRangeInfos("year(col1)", ImmutableList.of("1")),
                ImmutableList.of(new RangeInfo("year(col1)", Optional.empty(), Optional.of(1))));

        assertEquals(
                convertPartitionDescriptionsToRangeInfos("year(col1)", ImmutableList.of("1", "10")),
                ImmutableList.of(
                        new RangeInfo("year(col1)", Optional.empty(), Optional.of(1)),
                        new RangeInfo("year(col1)", Optional.of(1), Optional.of(10))));

        assertEquals(
                convertPartitionDescriptionsToRangeInfos("year(col1)", ImmutableList.of("1", "MAXVALUE")),
                ImmutableList.of(
                        new RangeInfo("year(col1)", Optional.empty(), Optional.of(1)),
                        new RangeInfo("year(col1)", Optional.of(1), Optional.empty())));
    }
}
