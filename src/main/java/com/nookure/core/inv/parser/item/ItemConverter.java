package com.nookure.core.inv.parser.item;

import com.nookure.core.inv.I18nAdapter;

public abstract class ItemConverter<T> {
  public abstract T convert(Item item, I18nAdapter adapter);

  public abstract Item convert(T t);
}
