import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nycschool.repository.NYCSchoolRepository
import com.example.nycschool.viewmodel.NYCSchoolViewModel

/**
 * This view model factory class is used to create ViewModel instance, ViewModel requires
 * repository in constructor
 */
class NYCSchoolViewModelFactory(
    private val schoolRepository: NYCSchoolRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NYCSchoolViewModel(schoolRepository) as T
    }
}