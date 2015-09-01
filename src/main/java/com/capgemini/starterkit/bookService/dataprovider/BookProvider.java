package com.capgemini.starterkit.bookService.dataprovider;

import java.util.Collection;

import com.capgemini.starterkit.bookService.dataprovider.data.BookVO;
import com.capgemini.starterkit.bookService.dataprovider.impl.BookProviderImpl;

public interface BookProvider {
	BookProvider INSTANCE = new BookProviderImpl();

	Collection<BookVO> findBooks(String title);

	Void saveBook(BookVO book);
}
