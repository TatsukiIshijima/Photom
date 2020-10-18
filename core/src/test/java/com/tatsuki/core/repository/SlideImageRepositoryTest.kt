package com.tatsuki.core.repository

class SlideImageRepositoryTest {

//    うまくモックでテストできそうにないのでコメントアウト
//    private lateinit var repository: SlideImageRepository
//    private lateinit var storage: FirebaseStorage
//    private lateinit var storageRef: StorageReference
//
//    @Before
//    fun initialize() {
//        storage = Mockito.mock(FirebaseStorage::class.java)
//        storageRef = Mockito.mock(StorageReference::class.java)
//
//        repository = SlideImageRepository(storage)
//
//        whenever(storage.reference).thenReturn(storageRef)
//        whenever(storage.reference.child("")).thenReturn(storageRef)
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun 朝の部の画像の参照リストが取得できること() {
//        runBlocking {
//            repository.fetchMorningRef().collect { state ->
//                when (state) {
//                    is State.Success -> {
//                        assertThat(state.data).isNull()
//                    }
//                    is State.Failed -> {
//                        assertThat(state.exception).isNotNull()
//                    }
//                }
//            }
//        }
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun 昼の部の画像の参照リストが取得できること() {
//        runBlocking {
//            repository.fetchNoonRef().collect {
//
//            }
//        }
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun 夜の部の画像の参照リストが取得できること() {
//        runBlocking {
//            repository.fetchEveningRef().collect {
//
//            }
//        }
//    }
}