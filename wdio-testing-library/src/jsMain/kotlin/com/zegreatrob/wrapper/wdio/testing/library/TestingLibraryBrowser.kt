package com.zegreatrob.wrapper.wdio.testing.library

import com.zegreatrob.wrapper.wdio.WebdriverElement
import com.zegreatrob.wrapper.wdio.browser
import com.zegreatrob.wrapper.wdio.testing.library.external.setupBrowser
import kotlinx.coroutines.await

object TestingLibraryBrowser {
    private val extendedWdioBrowser by lazy { setupBrowser(browser) }

    suspend fun getByText(text: String) = WebdriverElement(finder = { extendedWdioBrowser.getByText(text).await() })
        .apply { waitToExist() }

    suspend fun findByText(text: String) = WebdriverElement(finder = { extendedWdioBrowser.findByText(text).await() })
        .apply { waitToExist() }

    suspend fun queryByText(text: String) = WebdriverElement(finder = {
        extendedWdioBrowser.queryByText(text).await() ?: browser.`$`("element-with-text-$text-not-found").await()
    })
}
