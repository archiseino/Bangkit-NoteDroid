import com.example.dicodingsimplecleanarchitecture.data.IMessageDataSource
import com.example.dicodingsimplecleanarchitecture.data.MessageDataSource
import com.example.dicodingsimplecleanarchitecture.data.MessageRepository
import com.example.dicodingsimplecleanarchitecture.domain.IMessageRepository
import com.example.dicodingsimplecleanarchitecture.domain.MessageInteractor
import com.example.dicodingsimplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}
