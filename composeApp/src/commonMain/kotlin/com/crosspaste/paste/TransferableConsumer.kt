package com.crosspaste.paste

import java.awt.datatransfer.Transferable

interface TransferableConsumer {

    suspend fun consume(
        transferable: Transferable,
        source: String?,
        remote: Boolean,
    )
}