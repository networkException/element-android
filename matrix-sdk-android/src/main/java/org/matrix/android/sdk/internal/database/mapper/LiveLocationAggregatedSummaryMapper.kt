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

import org.matrix.android.sdk.api.session.events.model.toContent
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.api.session.room.model.livelocation.LiveLocationAggregatedSummary
import org.matrix.android.sdk.api.session.room.model.message.MessageBeaconLocationDataContent
import org.matrix.android.sdk.internal.database.model.livelocation.LiveLocationAggregatedSummaryEntity

internal object LiveLocationAggregatedSummaryMapper {

    fun map(entity: LiveLocationAggregatedSummaryEntity): LiveLocationAggregatedSummary {
        return LiveLocationAggregatedSummary(
                eventId = entity.eventId,
                roomId = entity.roomId,
                isActive = entity.isActive,
                endOfLiveTimestampAsMilliseconds = entity.endOfLiveTimestampAsMilliseconds,
                lastLocationDataContent = ContentMapper.map(entity.lastLocationContent).toModel<MessageBeaconLocationDataContent>()
        )
    }

    fun map(model: LiveLocationAggregatedSummary): LiveLocationAggregatedSummaryEntity {
        return LiveLocationAggregatedSummaryEntity(
                eventId = model.eventId,
                roomId = model.roomId,
                isActive = model.isActive,
                endOfLiveTimestampAsMilliseconds = model.endOfLiveTimestampAsMilliseconds,
                lastLocationContent = ContentMapper.map(model.lastLocationDataContent.toContent())
        )
    }
}
