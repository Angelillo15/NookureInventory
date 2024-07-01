package com.nookure.core.inv;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public abstract class OpenInventoryRegistry<T> {
  private final BiMap<UUID, T> registry = HashBiMap.create();

  public void register(UUID uuid, T value) {
    registry.put(uuid, value);
  }

  @Nullable
  public T get(UUID uuid) {
    return registry.get(uuid);
  }

  @Nullable
  public UUID get(T value) {
    return registry.inverse().get(value);
  }

  public void unregister(UUID uuid) {
    registry.remove(uuid);
  }

  public void unregister(T value) {
    registry.inverse().remove(value);
  }

  public boolean contains(UUID uuid) {
    return registry.containsKey(uuid);
  }

  public boolean contains(T value) {
    return registry.containsValue(value);
  }

  public BiMap<UUID, T> getRegistry() {
    return registry;
  }

  public void clear() {
    registry.clear();
  }

  public int size() {
    return registry.size();
  }

  public Set<T> values() {
    return registry.values();
  }

  public Set<UUID> keys() {
    return registry.keySet();
  }
}
