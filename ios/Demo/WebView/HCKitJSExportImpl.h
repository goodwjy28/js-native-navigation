//
//  BotsKitJSExportImpl.h
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HCKitJSExport.h"
#import "HCJSCoreBaseViewController.h"
NS_ASSUME_NONNULL_BEGIN

@interface HCKitJSExportImpl : NSObject <HCKitJSExport>

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext;

@end

NS_ASSUME_NONNULL_END
