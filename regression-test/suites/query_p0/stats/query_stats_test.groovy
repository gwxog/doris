// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

suite("query_stats_test") {
    sql "use test_query_db"
    sql "create table baseall_stat like baseall"
    sql "insert into baseall_stat select * from baseall"

    sql "admin set frontend config (\"enable_query_hit_stats\"=\"true\");"
    sql "clean all query stats"

    explain {
        sql("select k1 from baseall_stat where k1 = 1")
    }

    qt_sql "show query stats from baseall_stat"

    sql "select k1 from baseall_stat where k0 = 1"
    sql "select k4 from baseall_stat where k2 = 1991"

    qt_sql "show query stats from baseall_stat"
    qt_sql "show query stats from baseall_stat all"
    qt_sql "show query stats from baseall_stat all verbose"
    sql "admin set frontend config (\"enable_query_hit_stats\"=\"false\");"
    sql "drop table baseall_stat"
}
