/*
 * Copyright (c) 2022 The Matrix.org Foundation C.I.C.
 *
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

package org.matrix.android.sdk.internal.database.mapper

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.amshove.kluent.internal.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.matrix.android.sdk.api.session.events.model.Content
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.api.session.room.model.livelocation.LiveLocationShareAggregatedSummary
import org.matrix.android.sdk.api.session.room.model.message.MessageBeaconLocationDataContent
import org.matrix.android.sdk.internal.database.model.livelocation.LiveLocationShareAggregatedSummaryEntity

class LiveLocationShareAggregatedSummaryMapperTest {

    private val mapper = LiveLocationShareAggregatedSummaryMapper()

    @Before
    fun setUp() {
        mockkStatic("org.matrix.android.sdk.internal.database.mapper.ContentMapperKt")
        mockkStatic("org.matrix.android.sdk.api.session.events.model.EventKt")
    }

    @After
    fun tearDown() {
        unmockkStatic("org.matrix.android.sdk.internal.database.mapper.ContentMapperKt")
        unmockkStatic("org.matrix.android.sdk.api.session.events.model.EventKt")
    }

    @Test
    fun `given an entity then result should be mapped correctly`() {
        val userId = "userId"
        val timeout = 123L
        val isActive = true
        val lastKnownLocationContent = "lastKnownLocationContent"
        val messageBeaconLocationDataContent = MessageBeaconLocationDataContent()
        val entity = LiveLocationShareAggregatedSummaryEntity(
                userId = userId,
                isActive = isActive,
                endOfLiveTimestampMillis = timeout,
                lastLocationContent = lastKnownLocationContent
        )
        val content = mockk<Content>()
        every { ContentMapper.map(lastKnownLocationContent) } returns content
        every { content.toModel<MessageBeaconLocationDataContent>() } returns messageBeaconLocationDataContent

        val summary = mapper.map(entity)

        val expectedSummary = LiveLocationShareAggregatedSummary(
                userId = userId,
                isActive = isActive,
                endOfLiveTimestampMillis = timeout,
                lastLocationDataContent = messageBeaconLocationDataContent
        )
        assertEquals(expectedSummary, summary)
    }
}