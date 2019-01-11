//
//  HCRouter.h
//  Demo
//
//  Created by 刘海川 on 2019/1/10.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface HCRouter : NSObject

+ (instancetype)instance;

- (UIViewController *)push:(NSString *)pathName;

@end

NS_ASSUME_NONNULL_END
