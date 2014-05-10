package uaiContacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import uaiContacts.model.Contact;
import uaiContacts.repository.ContactRepository;
import uaiContacts.vo.ContactListVO;

@Service
@Transactional
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Transactional(readOnly = true)
	public ContactListVO findAll(int page, int maxResults) {
		Page<Contact> result = executeQueryFindAll(page, maxResults);
		
		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindAll(lastPage, maxResults);
		}
		
		return buildResult(result);
	}
	
	public void save(Contact contact) {
		contactRepository.save(contact);
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(int contactId) {
		contactRepository.delete(contactId);
	}
	
	public ContactListVO findByNameLike(int page, int maxResults, String name) {
		Page<Contact> result = executeQueryFindByName(page, maxResults, name);
		
		if (shouldExecuteSameQueryInLastPage(page, result)) {
			int lastPage = result.getTotalPages() - 1;
			result = executeQueryFindByName(lastPage, maxResults, name);
		}
		
		return buildResult(result);
	}
	
	private boolean shouldExecuteSameQueryInLastPage(int page, Page<Contact> result) {
		return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
	}
	
	private Page<Contact> executeQueryFindAll(int page, int maxResults) {
		final PageRequest pageRequest = new PageRequest(page, maxResults);
		
		return contactRepository.findAll(pageRequest);
	}
	
	private ContactListVO buildResult(Page<Contact> result) {
		return new ContactListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
	}
	
	private Page<Contact> executeQueryFindByName(int page, int maxResults, String name) {
		final PageRequest pageRequest = new PageRequest(page, maxResults);
		
		return contactRepository.findByNameLike(pageRequest, "%" + name + "%");
	}
	
	private boolean isUserAfterOrOnLastPage(int page, Page<Contact> result) {
		return page >= result.getTotalPages() - 1;
	}
	
	private boolean hasDataInDataBase(Page<Contact> result) {
		return result.getTotalElements() > 0;
	}
}
