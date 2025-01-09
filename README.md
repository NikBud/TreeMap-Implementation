# TreeMap Implementation in Java

This repository provides a custom implementation of a **TreeMap** in Java, designed with balancing based on the principles of a **Red-Black Tree**.

## Features

- **Custom TreeMap Implementation**:
    - Implements the `Map` interface to provide key-value mappings.
    - Supports common operations like insertion, deletion, and retrieval.

- **Red-Black Tree Balancing**:
    - Ensures logarithmic time complexity for search, insert, and delete operations.
    - Maintains tree balance through rotations and recoloring.

- **Node Structure**:
    - Each node contains references to its parent, left, and right children.
    - Nodes have attributes such as color (`RED` or `BLACK`) and side (`LEFT`, `RIGHT`, or `ROOT`) for efficient tree management.

## Key Classes

### `Entry<K, V>`
Represents a single node in the tree with the following properties:
- **Key and Value**: Stores the key-value pair for each entry.
- **Parent and Children**: References to the parent, left, and right nodes.
- **Side**: Indicates the position of the node relative to its parent (`LEFT`, `RIGHT`, `ROOT`).
- **Color**: Helps maintain the balance of the tree (`RED` or `BLACK`).

### `TreeMapImpl<K, V>` (Main TreeMap Class)
- Provides methods to:
    - Insert new key-value pairs.
    - Remove entries by key.
    - Retrieve values based on keys.
    - Perform balancing through red-black tree algorithms.

## Red-Black Tree Properties
The tree adheres to the following properties:
1. Each node is either red or black.
2. The root node is always black.
3. Red nodes cannot have red children (no two consecutive red nodes).
4. Every path from a node to its descendants contains the same number of black nodes.
5. New insertions and deletions trigger rebalancing operations (rotations and recoloring).

## Example Usage
```java
TreeMapImpl<Integer, String> treeMap = new TreeMapImpl<>();

// Inserting elements
treeMap.put(1, "One");
treeMap.put(2, "Two");
treeMap.put(3, "Three");

// Retrieving an element
System.out.println(treeMap.get(2)); // Output: Two

// Removing an element
treeMap.remove(1);
```

## Advantages
- Efficient for large datasets due to balanced tree structure.
- Guarantees `O(log n)` complexity for search, insert, and delete operations.
 