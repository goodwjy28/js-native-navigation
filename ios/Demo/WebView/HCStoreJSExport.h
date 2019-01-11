//
//  BotsStoreJSExport.h
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>

NS_ASSUME_NONNULL_BEGIN

@protocol HCStoreJSExport <JSExport>
JSExportAs(userStore,
           - (void)userDefaultSave:(NSString *)key value:(NSDictionary *)value result:(JSValue *)result
           );

JSExportAs(userRead,
           - (void)userDefaultRead:(NSString *)key result:(JSValue *)result
           );

@end

NS_ASSUME_NONNULL_END
