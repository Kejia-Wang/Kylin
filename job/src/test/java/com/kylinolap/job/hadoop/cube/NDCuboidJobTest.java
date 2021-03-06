/*
 * Copyright 2013-2014 eBay Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kylinolap.job.hadoop.cube;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.util.ToolRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kylinolap.common.util.LocalFileMetadataTestCase;

/**
 * @author George Song (ysong1)
 * 
 */
public class NDCuboidJobTest extends LocalFileMetadataTestCase {

    private Configuration conf;

    @Before
    public void setup() throws Exception {
        conf = new Configuration();
        // conf.set("fs.default.name", "file:///");
        // conf.set("mapred.job.tracker", "local");

        // for local runner out-of-memory issue
        conf.set("mapreduce.task.io.sort.mb", "10");

        createTestMetadata();
    }

    @After
    public void after() throws Exception {
        cleanupTestMetadata();
    }

    @Test
    public void testJob6D() throws Exception {
        String input = "src/test/resources/data/base_cuboid/";
        String output = "target/test-output/6d_cuboid";
        String cubeName = "test_kylin_cube_with_slr_1_new_segment";
        String segmentName = "20130331080000_20131212080000";
        String jobname = "6d_cuboid";
        String level = "1";

        FileUtil.fullyDelete(new File(output));

        String[] args = { "-input", input, "-cubename", cubeName, "-segmentname", segmentName, "-output", output, "-jobname", jobname, "-level", level };
        assertEquals("Job failed", 0, ToolRunner.run(conf, new NDCuboidJob(), args));
    }

    @Test
    public void testJob5D() throws Exception {
        final String input = "src/test/resources/data/6d_cuboid/";
        final String output = "target/test-output/5d_cuboid";
        final String cubeName = "test_kylin_cube_with_slr_1_new_segment";
        String segmentName = "20130331080000_20131212080000";
        String jobname = "5d_cuboid";
        String level = "2";

        FileUtil.fullyDelete(new File(output));

        String[] args = { "-input", input, "-cubename", cubeName, "-segmentname", segmentName, "-output", output, "-jobname", jobname, "-level", level };
        assertEquals("Job failed", 0, ToolRunner.run(conf, new NDCuboidJob(), args));
    }
}
