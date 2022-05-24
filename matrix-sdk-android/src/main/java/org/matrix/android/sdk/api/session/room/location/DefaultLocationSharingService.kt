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

package org.matrix.android.sdk.api.session.room.location

import androidx.lifecycle.LiveData
import com.zhuinden.monarchy.Monarchy
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.matrix.android.sdk.api.session.room.model.livelocation.LiveLocationShareAggregatedSummary
import org.matrix.android.sdk.internal.database.mapper.LiveLocationShareAggregatedSummaryMapper
import org.matrix.android.sdk.internal.database.model.livelocation.LiveLocationShareAggregatedSummaryEntity
import org.matrix.android.sdk.internal.database.query.findRunningLiveInRoom
import org.matrix.android.sdk.internal.di.SessionDatabase

// TODO add unit tests
internal class DefaultLocationSharingService @AssistedInject constructor(
        @Assisted private val roomId: String,
        @SessionDatabase private val monarchy: Monarchy,
        private val liveLocationShareAggregatedSummaryMapper: LiveLocationShareAggregatedSummaryMapper,
) : LocationSharingService {

    @AssistedFactory
    interface Factory {
        fun create(roomId: String): DefaultLocationSharingService
    }

    override fun getRunningLiveLocationShareSummaries(): LiveData<List<LiveLocationShareAggregatedSummary>> {
        return monarchy.findAllMappedWithChanges(
                { LiveLocationShareAggregatedSummaryEntity.findRunningLiveInRoom(it, roomId = roomId) },
                { liveLocationShareAggregatedSummaryMapper.map(it) }
        )
    }
}