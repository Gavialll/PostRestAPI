package com.example.serverpost.other;

import java.util.*;

public class Mains {

//    public static void main(String[] args) {
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("Ivan", 100);
//        map.put("Taras", 120);
//        map.put("Roman", 45);
//
//        Integer rezult = 0;
//        for(Integer value : map.values()) {
//            if(value > rezult) rezult = value;
//        }
//
//        for(String name : map.keySet()){
//            if(map.get(name).equals(rezult)){
//                System.out.println(name);
//                break;
//            }
//        }
//    }

//    public static void main(String[] args) {
//
//    }
//
//
//    public static void setDuplicateElement(Integer[] array){
//        Set<Integer> set = new HashSet<>(new ArrayList<>(Arrays.asList(array)));
//
//        System.out.println(set.size() != array.length);
//    }
//    public static String duplicateArray(Integer[] array){
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("Duplicate element: ");
//        for(int i = 0; i < array.length; i++) {
//            for(int j = i + 1; j < array.length; j++) {
//                if(array[i].equals(array[j])){
//                    buffer.append(array[i] + ", ");
//                }
//            }
//        }
//        return buffer.toString();
//    }

//    public static void main(String[] args) {
//        List<User> users = new ArrayList<>(init());
//        AtomicLong id = new AtomicLong(3);
//        AtomicLong AuthorizationID = new AtomicLong();
//        AtomicBoolean flag = new AtomicBoolean(false);
//
//        while(true) {
//            System.out.print("Enter password: ");
//            String password = new Scanner(System.in).nextLine();
//            for(User user : users) {
//                if(user.getPassword().equals(password)) {
//                    System.out.println("Authorization Complete");
//                    AuthorizationID.set(user.getId());
//                    flag.set(true);
//                    break;
//                }
//            }
//                while(flag.get()) {
//                    System.out.print("Enter Action: ");
//                    String action = new Scanner(System.in).nextLine();
//                    switch(action) {
//                        case "add post": {
//                            System.out.println("Add Post");
//                            for(User user : users) {
//                                System.out.println(user.getId() + " " + user.getName());
//                            }
//                            System.out.print("Enter User Id: ");
//                            int i = new Scanner(System.in).nextInt();
//                            System.out.print("Enter Post Name: ");
//                            users.stream().filter(u -> i == u.getId())
//                                    .findAny()
//                                    .get()
//                                    .getPostList()
//                                    .add(Post.builder()
//                                            .id(id.getAndIncrement())
//                                            .description("new Scanner(System.in).nextLine()")
//                                            .name(new Scanner(System.in).nextLine())
//                                            .commentList(new ArrayList<>())
//                                            .build());
//                            System.out.println("Ok");
//                            break;
//                        }
//                        case "delete post": {
//                            System.out.println("Delete Post By Id");
//                            for(User user : users) {
//                                System.out.println(user.getId() + " " + user.getName());
//                            }
//
//                            System.out.print("Enter User Id: ");
//                            int idUser = new Scanner(System.in).nextInt();
//                            for(User value : users) {
//                                long idValue = value.getId();
//                                if(idValue == idUser) value.getPostList().forEach(post -> System.out.println(post.getId() + " " + post.getName()));
//                            }
//
//                            System.out.print("Enter Post Id: ");
//                            int idPost = new Scanner(System.in).nextInt();
//
//                            for(User user : users) {
//                                if(user.getId() == idUser) {
//                                    user.getPostList().removeIf(post -> post.getId() == idPost);
//                                    System.out.println("Ok");
//                                }
//                            }
//                            break;
//                        }
//                        case "add user": {
//                            System.out.println("Add User");
//                            Scanner scanner = new Scanner(System.in);
//                            User user = new User();
//                            user.setId(id.incrementAndGet());
//                            System.out.print("Enter Name: ");
//                            user.setName(scanner.nextLine());
//                            user.setEmail("1232@gmail.com");
//                            user.setPostList(new ArrayList<>());
//                            users.add(user);
//                            System.out.println("Ok");
//                            break;
//                        }
//                        case "add comment": {
//                            System.out.println("Add Comment");
//                            users.forEach(user -> System.out.println(user.getId() + " " + user.getName()));
//                            System.out.print("Enter User Id: ");
//                            long idUser = new Scanner(System.in).nextLong();
//                            users.forEach(user -> {
//                                if(user.getId() == idUser) {
//                                    user.getPostList().forEach(post -> {
//                                        System.out.println(post.getId() + " " + post.getName());
//                                    });
//
//                                    System.out.print("Enter Post Id: ");
//                                    int idPost = new Scanner(System.in).nextInt();
//                                    user.getPostList().forEach(post -> {
//                                        System.out.println(post.getName());
//                                        if(post.getId() == idPost) {
//                                            post.getCommentList().forEach(comment -> {
//                                                System.out.println(comment.getMessage());
//                                            });
//                                            System.out.print("Enter Comment: ");
//                                            String message = new Scanner(System.in).nextLine();
//                                            post.getCommentList().add(new Comment(id.getAndIncrement(), AuthorizationID.get(), "        " + message));
//                                        }
//                                    });
//                                }
//                            });
//                            System.out.println("Ok");
//                            break;
//
//                        }
//                        case "info": {
//                            run(users);
//                            break;
//                        }
//                        case "exit": {
//                            flag.set(false);
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//
//    public static void run(List<User> userList) {
//        for(User user : userList) {
//            System.out.println(user.getName());
//            if(! user.getPostList().isEmpty()) {
//                for(Post post : user.getPostList()) {
//                    System.out.println("    " + post.getName());
//                    for(Comment comment : post.getCommentList()) {
//                        System.out.print(comment.message);
//                        String name = userList.stream()
//                                .filter(user1 -> user1.getId() == comment.getSenderId())
//                                .findAny()
//                                .get()
//                                .getName();
//                        System.out.println(" User name: " + name);
//                    }
//                }
//            } else System.out.println("    No Post");
//        }
//    }
//
//    public static List<User> init(){
//        String emailDefault = "123@gmail";
//
//        List<Comment> commentList = new ArrayList<>(Arrays.asList(
//                Comment.builder()
//                        .id(1L)
//                        .senderId(1L)
//                        .message("        Car comment Ivan")
//                        .build(),
//                Comment.builder()
//                        .id(2L)
//                        .senderId(2L)
//                        .message("        Car comment Taras")
//                        .build(),
//                Comment.builder()
//                        .id(3L)
//                        .senderId(1L)
//                        .message("        House comment Ivan")
//                        .build(),
//                Comment.builder()
//                        .id(4L)
//                        .senderId(2L)
//                        .message("        Plate comment Taras")
//                        .build()));
//
//
//        List<Post> postList = Arrays.asList(
//                Post.builder()
//                        .id(1L)
//                        .name("Car")
//                        .description("Car description")
//                        .commentList(new ArrayList<>(Arrays.asList(commentList.get(0), commentList.get(1))))
//                        .build(),
//                Post.builder()
//                        .id(2L)
//                        .name("House")
//                        .description("House description")
//                        .commentList(new ArrayList<>(Arrays.asList(commentList.get(2))))
//                        .build(),
//                Post.builder()
//                        .id(3L)
//                        .name("Plate")
//                        .description("Plate description")
//                        .commentList(new ArrayList<>(Arrays.asList(commentList.get(3))))
//                        .build()
//        );
//
//
//        return Arrays.asList(
//                User.builder()
//                        .id(1L)
//                        .email(emailDefault)
//                        .name("Ivan")
//                        .postList(new ArrayList<>(Arrays.asList(postList.get(0), postList.get(1))))
//                        .password("333")
//                        .build(),
//                User.builder()
//                        .id(2L)
//                        .email(emailDefault)
//                        .name("Taras")
//                        .postList(new ArrayList<>())
//                        .password("222")
//                        .build(),
//                User.builder()
//                        .id(3L)
//                        .email(emailDefault)
//                        .name("Roman")
//                        .postList(new ArrayList<>(Arrays.asList(postList.get(2))))
//                        .password("111")
//                        .build()
//        );
//    }
}
