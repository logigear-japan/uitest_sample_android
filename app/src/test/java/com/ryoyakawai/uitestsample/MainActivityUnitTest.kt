package com.ryoyakawai.uitestsample

import com.ryoyakawai.uitestsample.UnitTestUtils.*
import com.ryoyakawai.uitestsample.api.APIClient.setConnection
import com.ryoyakawai.uitestsample.api.response.SinglePostResponse
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals

import org.junit.Test

internal class MockServerDispatcher {
    // Array<SinglePostResponse>
    val mockedResponse = arrayOf(
        SinglePostResponse(
            0,
            0,
            "name00",
            "email00@example.com",
            "body00"
        ),
        SinglePostResponse(
            1,
            1,
            "name01",
            "email01@example.com",
            "body01"
        )
    )
    internal inner class sample00 : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(mockedResponse))
        }
    }

    internal inner class Resp400 : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    internal inner class Resp500 : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(500)
        }
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityUnitTest {
    private var mMockTestUtils = UnitTestUtils()

    @Test
    fun sampleUnitDataFetchSuccessTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().sample00()
        }
        val expectedResponse = MockServerDispatcher().mockedResponse

        var mMainActivityPresenter = MainActivityPresenter()
        var mMainActivityViewContract = mock<MainActivityViewContract>()

        mMainActivityPresenter.setView(mMainActivityViewContract)

        mMockTestUtils.resetRx()
        mMockTestUtils.switchToTrampolineScheduler()

        mMockTestUtils.startMockServer()
        var mApiConnection = mMockTestUtils.setupMockServer()
        setConnection(mApiConnection)

        mMainActivityPresenter.getJsonSampleResponse()
        argumentCaptor<Array<SinglePostResponse>>().apply {
            verify(mMainActivityViewContract, times(1)).handleSuccess(capture())

            for (i in 0 until expectedResponse.size) {
                assertEquals(expectedResponse[i].postId, this.firstValue[i].postId)
                assertEquals(expectedResponse[i].id, this.firstValue[i].id)
                assertEquals(expectedResponse[i].name, this.firstValue[i].name)
                assertEquals(expectedResponse[i].email, this.firstValue[i].email)
                assertEquals(expectedResponse[i].body, this.firstValue[i].body)

                val expected = expectedResponse[i]
                val actual = this.firstValue[i]
                mMockTestUtils.assertDataClass(expected, actual)
            }

        }
        mMockTestUtils.shutdownMockServer()
    }

    @Test
    fun sampleUnit400BadRequestTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().Resp400()
        }

        var mMainActivityPresenter = MainActivityPresenter()
        var mMainActivityViewContract = mock<MainActivityViewContract>()

        mMainActivityPresenter.setView(mMainActivityViewContract)

        mMockTestUtils.resetRx()
        mMockTestUtils.switchToTrampolineScheduler()

        mMockTestUtils.startMockServer()
        var mApiConnection = mMockTestUtils.setupMockServer()
        setConnection(mApiConnection)

        mMainActivityPresenter.getJsonSampleResponse()
        argumentCaptor<String>().apply {
            verify(mMainActivityViewContract, times(1)).handleError(capture())
            assertEquals("HTTP 400 Client Error", this.firstValue)
        }
        mMockTestUtils.shutdownMockServer()
    }

    @Test
    fun sampleUnit500ServerErrorTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().Resp500()
        }

        var mMainActivityPresenter = MainActivityPresenter()
        var mMainActivityViewContract = mock<MainActivityViewContract>()

        mMainActivityPresenter.setView(mMainActivityViewContract)

        mMockTestUtils.resetRx()
        mMockTestUtils.switchToTrampolineScheduler()

        mMockTestUtils.startMockServer()
        var mApiConnection = mMockTestUtils.setupMockServer()
        setConnection(mApiConnection)

        mMainActivityPresenter.getJsonSampleResponse()
        argumentCaptor<String>().apply {
            verify(mMainActivityViewContract, times(1)).handleError(capture())
            assertEquals("HTTP 500 Server Error", this.firstValue)
        }
        mMockTestUtils.shutdownMockServer()
    }
}
