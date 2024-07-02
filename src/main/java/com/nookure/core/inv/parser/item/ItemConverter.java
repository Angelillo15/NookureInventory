package com.nookure.core.inv.parser.item;

import com.nookure.core.inv.I18nAdapter;

public abstract class ItemConverter<T, P> {
  public abstract T convert(Item item, I18nAdapter<P> adapter);

  public abstract Item convert(T t);
}
